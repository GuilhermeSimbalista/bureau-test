import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Upload from '@/views/Upload.vue';
import Translators from '@/views/Translators.vue';
import Documents from '@/views/Documents.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/upload',
    name: 'Upload',
    component: Upload,
  },
  {
    path: '/translators',
    name: 'Translators',
    component: Translators,
  },
  {
    path: '/documents',
    name: 'Documents',
    component: Documents,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
