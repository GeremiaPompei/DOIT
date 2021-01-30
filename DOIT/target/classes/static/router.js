import('./components/role.js');
const key = "DOIT-key";

const routes = [
  {path: '/cerca', component: () => import('./components/cerca.js')},
  {path: '/login', component: () => import('./components/login.js')},
  {path: '/signin', component: () => import('./components/signin.js')},
  {path: '/project-proposer', component: () => import('./components/project-proposer.js')},
  {path: '/program-manager', component: () => import('./components/program-manager.js')},
  {path: '/designer', component: () => import('./components/designer.js')},
  {path: '/project-manager', component: () => import('./components/project-manager.js')},
  {path: '/user-main', component: () => import('./components/user-main.js')},
  {path: '/create-project', component: () => import('./components/create-project.js')},
  {path: '/category/:id', component: () => import('./components/category.js')},
  {path: '/project/:id', component: () => import('./components/project.js')},
  {path: '/list-projects/:role', component: () => import('./components/list-projects.js')},
  {path: '/history/:role', component: () => import('./components/history.js')},
  {path: '/list-notifications/:role', component: () => import('./components/list-notifications.js')},
  {path: '/list-categories/:role', component: () => import('./components/list-categories.js')},
  {path: '/manage-category/:role', component: () => import('./components/manage-category.js')},
  {path: '/user/:id', component: () => import('./components/user.js')},
  {path: '/send-pr/:role', component: () => import('./components/send-pr.js')},
  {path: '/manage-pr/:role', component: () => import('./components/manage-pr.js')},
  {path: '/team-rec', component: () => import('./components/team-rec.js')},
  {path: '/set-project-manager', component: () => import('./components/set-project-manager.js')},
  {path: '/manage-project-state', component: () => import('./components/manage-project-state.js')},
  {path: '/evaluate', component: () => import('./components/evaluate.js')},
  {path: '/manage-experience', component: () => import('./components/manage-experience.js')},
  {path: '/remove-project/:role', component: () => import('./components/remove-project.js')},
  {path: '/remove-project-pjm', component: () => import('./components/remove-project-pjm.js')},
  {path: '/', redirect: '/login'}
];

const app = new Vue({
  router: new VueRouter({routes}),
  data() {
    return {
      user: {},
      roles: [],
      loading: false
    }
  },
  created() {
    this.init();
  },
  methods: {
    async init() {
      var credential = JSON.parse(localStorage.getItem(key));
      this.user = localStorage.getItem(key);
      if(this.user) {
        this.roles = [];
        try {
          var res = await (await fetch('/api/user/my-roles?iduser='+credential.id+'&tokenuser='+credential.token)).json();
          if(res) {
              this.roles = res.roles;
            }
        }catch(e) {
          localStorage.removeItem(key);
        }
        }
    },
    load(val) {
      this.loading = val;
      this.init();
    }
  }
}).$mount('#app');