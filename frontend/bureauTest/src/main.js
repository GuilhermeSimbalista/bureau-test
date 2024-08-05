import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import setupBootstrapVue from './plugins/bootstrap-vue';

const app = createApp(App);
setupBootstrapVue(app);
app.use(router);
app.mount('#app');
