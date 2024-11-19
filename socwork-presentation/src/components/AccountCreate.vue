<script>
export default {
  data() {
    //data-binding: comment lier data avec template,
    return {
      inputs: { username: "", password: "" },
    };
  },
  methods: {
    async submit() {
      //   event.preventDefault();
      // console.log(this.inputs);
      //REMPLACER BY AXIOS
      const options = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.inputs),
      };
      const response = await fetch("http://localhost:8080/accounts", options);
      if (response.ok) {
        alert("Compte créé avec succès");
      } else {
        console.error("Dev is a failure!");
      }
    },
  },
};
</script>

<template>
  <h1>Créer un compte</h1>
  <form @submit.prevent="submit" novalidate>
    <label for="username">Nom d'utilisateur</label>
    <input
      type="text"
      name="username"
      id="username"
      v-model="inputs.username"
    />
    <label for="password">Password</label>
    <input
      type="password"
      name="password"
      id="password"
      v-model="inputs.password"
    />
    <div>
      <button type="submit">Créer un compte</button>
    </div>
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 20%;
  justify-content: center;
}
h1 {
  color: blue;
}
label::after {
  content: "*";
  color: red;
}
button {
  width: 50%;
}
div {
  text-align: center;
}
</style>
