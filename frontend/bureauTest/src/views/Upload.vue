<template>
  <b-container>
    <document-form></document-form>
  </b-container>
</template>

<script>
import DocumentForm from '@/components/DocumentForm.vue';

export default {
  components: {
    DocumentForm,
  },
};
</script>

<style scoped>
.b-container {
  max-width: 600px;
  margin: auto;
}
</style>
