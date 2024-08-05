<template>
  <b-container>
    <b-row class="justify-content-center text-center mb-4">
      <b-col cols="12">
        <h1 class="display-4 font-weight-bold">Bem-vindo ao Bureau Test</h1>
        <p class="lead">Escolha uma opção abaixo para começar</p>
      </b-col>
    </b-row>
    <b-row class="justify-content-center">
      <b-col cols="12" md="6" class="text-center mb-3">
        <b-card no-body class="shadow-sm">
          <b-card-header class="bg-green text-white">
            <i class="bi bi-people-fill"></i> Gerenciar Tradutores
          </b-card-header>
          <b-card-body>
            <b-button variant="green" class="button-green" @click="$router.push('/translators')">Ir para Tradutores</b-button>
          </b-card-body>
        </b-card>
      </b-col>
      <b-col cols="12" md="6" class="text-center mb-3">
        <b-card no-body class="shadow-sm">
          <b-card-header class="bg-green text-white">
            <i class="bi bi-file-earmark-text-fill"></i> Gerenciar Documentos
          </b-card-header>
          <b-card-body>
            <b-button variant="green" class="button-green" @click="$router.push('/documents')">Ir para Documentos</b-button>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: 'Home',
};
</script>

<style scoped>
.b-container {
  max-width: 1200px;
  margin: auto;
  padding: 20px;
}

h1.display-4 {
  margin-bottom: 10px;
}

.b-card {
  border: none;
  border-radius: 10px;
}

.b-card-header {
  font-size: 1.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.b-card-header i {
  margin-right: 10px;
}

.b-card-body {
  padding: 20px;
}

.bg-green {
  background-color: #64c79e !important;
}

.button-green {
  background-color: #64c79e;
  border-color: #64c79e;
  color: #fff;
}

.button-green:hover {
  background-color: #64c79e;
  border-color: #64c79e;
}
</style>
