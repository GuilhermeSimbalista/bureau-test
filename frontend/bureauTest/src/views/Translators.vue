<template>
  <b-container>
    <translator-list></translator-list>
  </b-container>
</template>

<script>
import TranslatorList from '@/components/TranslatorList.vue';

export default {
  components: {
    TranslatorList,
  },
};
</script>

<style scoped>
.b-container {
  max-width: 1200px;
  margin: auto;
}
</style>
