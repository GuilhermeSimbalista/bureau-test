<template>
  <b-container class="mt-3">
    <b-form @submit.prevent="saveDocument">
      <b-form-group label="Subject">
        <b-form-input v-model="document.subject" required></b-form-input>
      </b-form-group>
      <b-form-group label="Content">
        <b-form-textarea v-model="document.content" required></b-form-textarea>
      </b-form-group>
      <b-form-group label="Locale">
        <b-form-input v-model="document.locale"></b-form-input>
      </b-form-group>
      <b-form-group label="Author">
        <b-form-input v-model="document.author" required></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary" class="mt-3">Save</b-button>
    </b-form>

    <hr>

    <b-form @submit.prevent="uploadCSV">
      <b-form-file
        v-model="file"
        accept=".csv"
        placeholder="Choose a file..."
        drop-placeholder="Drop file here..."
      ></b-form-file>
      <b-button type="submit" variant="primary" class="mt-3">Upload CSV</b-button>
    </b-form>
  </b-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      document: {},
      file: null,
    };
  },
  methods: {
    async saveDocument() {
      try {
        if (this.document.id) {
          await axios.put(`http://localhost:8080/api/documents/${this.document.id}`, this.document);
        } else {
          await axios.post('http://localhost:8080/api/documents', this.document);
        }
        this.$emit('saved');
      } catch (error) {
        console.error(error);
        alert('Error saving document');
      }
    },
    async uploadCSV() {
      const formData = new FormData();
      formData.append('file', this.file);
      try {
        const response = await axios.post('http://localhost:8080/api/documents/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        alert(response.data);
        this.$emit('saved');
      } catch (error) {
        console.error(error);
        alert('Error uploading file');
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
