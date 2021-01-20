const Role = import('./components/role.js');
const key = "DOIT-key";

function createRouteRole(name) {
  return {path: '/'+name, component: () => import('./components/'+name+'.js')};
}

const routes = [
  {path: '/cerca', component: () => import('./components/cerca.js')},
  {path: '/login', name: 'login', component: () => import('./components/login.js')},
  {path: '/signin', name: 'signin', component: () => import('./components/signin.js')},
  createRouteRole('project-proposer'),
  createRouteRole('program-manager'),
  createRouteRole('designer'),
  createRouteRole('project-manager'),
  {path: '/user/', component: () => import('./components/user.js')},
  {path: '/create-project/', component: () => import('./components/create_project.js')},
  {path: '/', redirect: '/login'}
];

const app = new Vue({
  router: new VueRouter({routes}),
  data() {
    return {
      user: undefined,
      roles: [],
      loading: false
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.user = JSON.parse(localStorage.getItem(key));
      if(this.user) {
        this.roles = [];
        fetch('/api/user/', {method: 'POST', body: this.user}).then(res=>res.json()).then(res=>{
          res.roles.forEach(r=>this.roles.push(r));
          this.$router.replace({path: '/user/'});
        });
      }
    },
    load(val) {
      this.loading = val;
    }
  }
}).$mount('#app');