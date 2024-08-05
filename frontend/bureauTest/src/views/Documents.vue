<template>
  <b-container>
    <document-list></document-list>
  </b-container>
</template>

<script>
import DocumentList from '@/components/DocumentList.vue';

export default {
  components: {
    DocumentList,
  },
};
</script>

<style scoped>
.b-container {
  max-width: 1200px;
  margin: auto;
}
</style>
