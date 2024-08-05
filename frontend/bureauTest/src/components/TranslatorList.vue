<template>
  <b-container fluid class="p-4">
    <b-row class="align-items-center mb-4">
      <b-col>
        <h2 class="text-dark">Tradutor</h2>
      </b-col>
      <b-col class="text-end">
        <b-button variant="success" class="me-2" @click="newTranslator">
          <i class="bi bi-person-plus"></i> Novo Tradutor
        </b-button>
        <b-button variant="info" @click="showUploadModal">
          <i class="bi bi-upload"></i> Carregar CSV
        </b-button>
      </b-col>
    </b-row>
    
    <b-table striped hover :items="translators" :fields="fields" class="mb-4">
      <template #cell(actions)="data">
        <b-button size="sm" variant="warning" class="me-2" @click="editTranslator(data.item)">
          Editar
        </b-button>
        <b-button size="sm" variant="danger" @click="deleteTranslator(data.item.id)">
          Deletar
        </b-button>
      </template>
    </b-table>

    <div class="d-flex justify-content-center">
      <b-pagination
        v-model="currentPage"
        :total-rows="totalTranslators"
        :per-page="perPage"
        aria-controls="my-table"
        @input="handlePageChange"
      ></b-pagination>
    </div>

    <b-modal v-model="showModal" title="Translator">
      <translator-form :translator="selectedTranslator" @saved="fetchTranslators(currentPage)"></translator-form>
    </b-modal>

    <b-modal v-model="showUploadModalFlag" title="Carregar CSV" hide-footer>
      <b-form @submit.prevent="uploadCSV" class="mt-3">
        <div class="upload-container">
          <div 
            class="upload-dropzone"
            @dragover.prevent
            @drop.prevent="handleDrop"
          >
            <input 
              type="file" 
              class="upload-input" 
              @change="handleFileUpload"
              accept=".csv"
            />
            <div class="upload-text">
              Clique para selecionar um arquivo
            </div>
          </div>
        </div>
        <b-button type="submit" variant="primary" class="mt-3">Carregar</b-button>
      </b-form>
    </b-modal>
  </b-container>
</template>

<script>
import axios from 'axios';
import TranslatorForm from './TranslatorForm.vue';

export default {
  components: {
    TranslatorForm,
  },
  data() {
    return {
      translators: [],
      currentPage: 1,
      perPage: 10,
      totalTranslators: 0,
      totalPages: 0,
      showModal: false,
      showUploadModalFlag: false,
      selectedTranslator: {},
      file: null,
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'name', label: 'Nome' },
        { key: 'email', label: 'E-mail' },
        { key: 'sourceLanguage', label: 'Idioma de Origem' },
        { key: 'targetLanguage', label: 'Idioma Alvo' },
        { key: 'actions', label: 'Actions', sortable: false },
      ],
    };
  },
  created() {
    this.fetchTranslators(this.currentPage);
  },
  watch: {
    currentPage(newPage) {
      this.fetchTranslators(newPage);
    }
  },
  methods: {
    async fetchTranslators(page) {
      try {
        const response = await axios.get('http://localhost:8080/api/translators', {
          params: {
            page: this.currentPage - 1,
            size: this.perPage,
            sort: 'id',
            direction: 'asc',
          },
        });
        const data = response.data;
        this.translators = data.content;
        this.totalTranslators = data.totalPages * this.perPage;
      } catch (error) {
        console.error(error);
        alert('Error fetching translators');
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    showUploadModal() {
      this.showUploadModalFlag = true;
    },
    handleDrop(event) {
      const files = event.dataTransfer.files;
      if (files.length > 0) {
        this.file = files[0];
      }
    },
    handleFileUpload(event) {
      const files = event.target.files;
      if (files.length > 0) {
        this.file = files[0];
      }
    },
    async uploadCSV() {
      const formData = new FormData();
      formData.append('file', this.file);
      try {
        const response = await axios.post('http://localhost:8080/api/translators/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        alert(response.data);
        this.fetchTranslators();
        this.showUploadModalFlag = false;
      } catch (error) {
        console.error(error);
        alert('Error uploading file');
      }
    },
    newTranslator() {
      this.selectedTranslator = {};
      this.showModal = true;
    },
    editTranslator(translator) {
      this.selectedTranslator = { ...translator };
      this.showModal = true;
    },
    async deleteTranslator(id) {
      try {
        await axios.delete(`http://localhost:8080/api/translators/${id}`);
        this.fetchTranslators();
      } catch (error) {
        console.error(error);
        alert('Error deleting translator');
      }
    },
  },
};
</script>

<style scoped>
.b-container {
  max-width: 800px;
  margin: auto;
}

.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  border: 2px dashed #007bff;
  border-radius: 4px;
  position: relative;
}

.upload-dropzone {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.upload-input {
  position: absolute;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

.upload-text {
  text-align: center;
  color: #6c757d;
}
</style>
