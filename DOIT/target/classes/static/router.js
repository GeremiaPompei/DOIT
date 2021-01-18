const Role = import('./components/role.js');
const key = "DOIT-key";

function createRouteRole(name) {
  return {path: '/'+name+'/:user', component: () => import('./components/'+name+'.js')};
}

const routes = [
  {path: '/cerca', component: () => import('./components/cerca.js')},
  {path: '/login', name: 'login', component: () => import('./components/login.js')},
  createRouteRole('project-proposer'),
  createRouteRole('program-manager'),
  createRouteRole('designer'),
  createRouteRole('project-manager'),
  {path: '/user/:user', name: 'user', component: () => import('./components/user.js')},
  {path: '/create-project/:user', component: () => import('./components/create_project.js')},
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
      this.user = localStorage.getItem(key);
      if(this.user) {
        this.roles = [];
        fetch('/api/user/'+this.user).then(res=>res.json()).then(res=>{
          res.roles.forEach(r=>this.roles.push(r));
          this.$router.replace({path: '/user/'+this.user});
        });
      }
    },
    signin(user) {
      this.load(true);
      this.roles = [];
      fetch('/api/user/'+user).then(res=>res.json()).then(res=>{
        res.roles.forEach(r=>this.roles.push(r));
        this.$router.replace({path: '/user/'+user});
        localStorage.setItem(key, user);
       this.init();
        this.load(false);
      }).catch(err => {
        alert(err);
        this.load(false);
      });
    },
    login(user) {
      this.load(true);
      this.roles = [];
      fetch('/api/user/'+user).then(res=>res.json()).then(res=>{
        res.roles.forEach(r=>this.roles.push(r));
        this.$router.replace({path: '/user/'+user});
        localStorage.setItem(key, user);
       this.init();
        this.load(false);
      }).catch(err => {
        document.getElementById('login').style.borderColor = 'red';
        alert(err);
        this.load(false);
      });
    },
    logout() {
      this.user = undefined;
      this.roles = [];
      localStorage.removeItem(key);
      this.$router.replace({path: '/'});
    },
    load(val) {
      this.loading = val;
    }
  }
}).$mount('#app');