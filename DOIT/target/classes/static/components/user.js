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
                <li v-for="(role, index) in roles" key="index" @click="actionRole(role)">
                    <button>{{role.displayName+' '+role.val}}</button>
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
            async actionRole(role) {
                this.$emit('load',true);
                if(role.val=='-') {
                    await fetch('/api/role/'+this.$route.params.user+'/'+role.name, {method: 'DELETE'});
                    role.val='+';
                    this.user.roles = this.user.roles.filter(r=>role.displayName!=r.displayName);
                }else if(role.val=='+') {
                    await fetch('/api/role/'+this.$route.params.user+'/'+role.name+'/'+role.displayName, {method: 'PUT'});
                    role.val='-';
                    this.user.roles.push(role);
                }
                this.initializer();
                this.$emit('load',false);
            },
            initializer() {
                this.roles = [
                    createRole('project-proposer','Project Proposer'),
                    createRole('program-manager', 'Program Manager'),
                    createRole('designer', 'Designer')
                    ];
                this.roles.forEach(role => role.val = this.user.roles.filter(r=>role.name==r.name).length==0?'+':'-');
                this.$emit('login', this.user.id);
            },
            logout() {
                this.$emit('load',true);
                fetch('/api/user/logout', {
                    method: 'POST', 
                    body: localStorage.getItem(key) , 
                    headers: {'Content-Type': 'application/json'}})
                .then(res => res.json())
                .then(res => {
                    this.user = undefined;
                    this.initializer();
                    localStorage.setItem(key, undefined);
                    this.$emit('load',false);
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