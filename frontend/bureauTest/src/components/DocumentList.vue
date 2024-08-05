<template>
  <b-container fluid class="p-4">
    <b-row class="align-items-center mb-4">
      <b-col>
        <h2 class="text-dark">Documentos</h2>
      </b-col>
      <b-col class="text-end">
        <b-button variant="success" class="me-2" @click="newDocument">
          <i class="bi bi-file-earmark-plus"></i> Novo Documento
        </b-button>
        <b-button variant="info" @click="showUploadModal">
          <i class="bi bi-upload"></i> Carregar CSV
        </b-button>
      </b-col>
    </b-row>
    
    <b-table striped hover :items="documents" :fields="fields" class="mb-4">
      <template #cell(actions)="data">
        <b-button size="sm" variant="warning" class="me-2" @click="editDocument(data.item)">
          Editar
        </b-button>
        <b-button size="sm" variant="danger" @click="deleteDocument(data.item.id)">
          Deletar
        </b-button>
      </template>
    </b-table>

    <div class="d-flex justify-content-center">
      <b-pagination
        v-model="currentPage"
        :total-rows="totalDocuments"
        :per-page="perPage"
        aria-controls="my-table"
        @input="handlePageChange"
      ></b-pagination>
    </div>

    <b-modal v-model="showModal" title="Documento">
      <document-form :document="selectedDocument" @saved="fetchDocuments(currentPage)"></document-form>
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
import DocumentForm from './DocumentForm.vue';

export default {
  components: {
    DocumentForm,
  },
  data() {
    return {
      documents: [],
      currentPage: 1,
      perPage: 10,
      totalDocuments: 0,
      showModal: false,
      showUploadModalFlag: false,
      selectedDocument: {},
      file: null,
      fields: [
        { key: 'id', label: 'ID' },
        { key: 'subject', label: 'Assunto' },
        { key: 'content', label: 'Conteúdo' },
        { key: 'locale', label: 'Localidade' },
        { key: 'author', label: 'Autor' },
        { key: 'actions', label: 'Ações', sortable: false },
      ],
    };
  },
  created() {
    this.fetchDocuments(this.currentPage);
  },
  watch: {
    currentPage(newPage) {
      this.fetchDocuments(newPage);
    }
  },
  methods: {
    async fetchDocuments(page) {
      try {
        const response = await axios.get('http://localhost:8080/api/documents', {
          params: {
            page: page - 1,
            size: this.perPage,
            sort: 'id',
            direction: 'asc',
          },
        });
        const data = response.data;
        this.documents = data.content;
        this.totalDocuments = data.totalPages * this.perPage;
      } catch (error) {
        console.error(error);
        alert('Erro ao buscar documentos');
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
        const response = await axios.post('http://localhost:8080/api/documents/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        alert(response.data);
        this.fetchDocuments(this.currentPage);
        this.showUploadModalFlag = false;
      } catch (error) {
        console.error(error);
        alert('Erro ao carregar o arquivo');
      }
    },
    newDocument() {
      this.selectedDocument = {};
      this.showModal = true;
    },
    editDocument(document) {
      this.selectedDocument = { ...document };
      this.showModal = true;
    },
    async deleteDocument(id) {
      try {
        await axios.delete(`http://localhost:8080/api/documents/${id}`);
        this.fetchDocuments();
      } catch (error) {
        console.error(error);
        alert('Erro ao deletar documento');
      }
    },
  },
};
</script>

<style scoped>
.b-container {
  max-width: 1200px;
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
