export default Vue.component('user', {
    template: `
        <div class='container'>
            <p>Name</p>
            <h3 class="el">{{user.name}}</h3>
            <p>Surname</p>
            <h3 class="el">{{user.surname}}</h3>
            <p>Birth date</p>
            <h3 class="el">{{user.birthDate}}</h3>
            <p>Sex</p>
            <h3 class="el">{{user.sex}}</h3>
            <p>Roles</p>
            <ul>
                <li v-for="(role, index) in roles" key="index" @click="addRole(index)">
                    <button>{{role.displayName}}</button>
                </li>
            </ul>
            <button @click="logout">Logout</button>
        </div>
        `,
        data() {
            return {
                user: {},
                roles: undefined
            }
        },
        created() {
            this.$emit('load',true);
            fetch('/api/user/get', {
                method: 'POST', 
                body: localStorage.getItem(key) , 
                headers: {'Content-Type': 'application/json'}})
            .then(res => res.json())
            .then(res => {
                this.user = res;
                this.initializer();
                this.$emit('load',false);
            });
        },
        methods: {
            initializer() {
                this.roles = [
                    createRole('project-proposer','Project Proposer'),
                    createRole('program-manager', 'Program Manager'),
                    createRole('designer', 'Designer')
                    ];
            },
            addRole(index) {
                this.$router.push({path: '/category-list/'+this.roles[index].name});
            },
            logout() {
                this.$emit('load',true);
                fetch('/api/user/logout', {
                    method: 'POST', 
                    body: localStorage.getItem(key) , 
                    headers: {'Content-Type': 'application/json'}})
                .then(res => res.text())
                .then(res => {
                    this.user = undefined;
                    this.roles = undefined;
                    localStorage.removeItem(key);
                    this.$emit('load',false);
                    this.$router.replace({path: '/login'});
                });
            }
        }
});

function createRole(name, displayName) {
    return {
      name: name,
      displayName: displayName
    };
  }