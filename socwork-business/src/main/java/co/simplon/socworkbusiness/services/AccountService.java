package co.simplon.socworkbusiness.services;

import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.socworkbusiness.config.JwtProvider;
import co.simplon.socworkbusiness.dtos.AccountAuthentificate;
import co.simplon.socworkbusiness.dtos.AccountCreate;
import co.simplon.socworkbusiness.entities.Account;
import co.simplon.socworkbusiness.entities.Role;
import co.simplon.socworkbusiness.repositories.AccountRepository;
import co.simplon.socworkbusiness.repositories.RoleRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository repos;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepos;

    public AccountService(AccountRepository repos, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                          RoleRepository roleRepos) {
        this.repos = repos;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepos = roleRepos;
    }

    @Transactional
    public void create(AccountCreate inputs) {
        Account entity = new Account();
        entity.setUsername(inputs.username());
        entity.setPassword(passwordEncoder.encode(inputs.password()));

        Set<Role> roles = roleRepos.findByIsDefaultTrue();
        entity.setRoles(roles);

        repos.save(entity);
    }

    @Transactional
    public Object authentificate(AccountAuthentificate inputs) {
        String username = inputs.username();
        Account account = repos.findAllByUsernameIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException(username));

        List<String> roles = account.getRoles().stream().map(r -> r.getRole()).toList();

        String row = inputs.password();
        String encoded = account.getPassword();
        if (!passwordEncoder.matches(row, encoded)) {
            throw new BadCredentialsException(username);
        }

        return jwtProvider.create(username, roles);
    }

    public String getAccount() {
        return "Account";
    }
}
