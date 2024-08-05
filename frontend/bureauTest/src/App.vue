<template>
  <b-container fluid class="p-0">
    <b-navbar toggleable="lg" type="light" variant="light" class="border-bottom shadow-sm">
      <b-navbar-brand href="#">
        <img src="@/assets/bureau-icon.png" alt="Bureau Icon" class="d-inline-block align-top" width="100" height="30" />
      </b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/" exact>Home</b-nav-item>
          <b-nav-item to="/translators">Tradutor</b-nav-item>
          <b-nav-item to="/documents">Documento</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view></router-view>
  </b-container>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style scoped>
.b-navbar-brand img {
  margin-right: 10px;
}

.b-navbar-nav .nav-item {
  margin-right: 20px;
}

.b-navbar-nav .nav-link {
  font-weight: bold;
  color: #000 !important;
}

.b-navbar {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.border-bottom {
  border-bottom: 1px solid #e5e5e5 !important;
}
</style>