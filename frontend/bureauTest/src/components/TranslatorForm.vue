<template>
  <b-container class="mt-3">
    <b-form @submit.prevent="saveTranslator">
      <b-form-group label="Name">
        <b-form-input v-model="translator.name" required></b-form-input>
      </b-form-group>
      <b-form-group label="Email">
        <b-form-input v-model="translator.email" type="email" required></b-form-input>
      </b-form-group>
      <b-form-group label="Source Language">
        <b-form-input v-model="translator.sourceLanguage" required></b-form-input>
      </b-form-group>
      <b-form-group label="Target Language">
        <b-form-input v-model="translator.targetLanguage" required></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary" class="mt-3">Save</b-button>
    </b-form>
  </b-container>
</template>

<script>
import axios from 'axios';

export default {
  props: ['translator'],
  methods: {
    async saveTranslator() {
      try {
        if (this.translator.id) {
          await axios.put(`http://localhost:8080/api/translators/${this.translator.id}`, this.translator);
        } else {
          await axios.post('http://localhost:8080/api/translators', this.translator);
        }
        this.$emit('saved');
      } catch (error) {
        console.error(error);
        alert('Error saving translator');
      }
    },
  },
};
</script>

<style scoped>
.b-container {
  max-width: 600px;
  margin: auto;
}
</style>
