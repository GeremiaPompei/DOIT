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
            <p>Email</p>
            <h3 class="el">{{user.email}}</h3>
            <p>Roles</p>
            <ul>
                <li v-for="(role, index) in roles" key="index" @click="addRole(index)">
                    <button>{{role.type}}</button>
                </li>
            </ul>
            <button @click="logout">Logout</button>
        </div>
        `,
        data() {
            return {
                user: {},
                roles: []
            }
        },
        created() {
            var credential = JSON.parse(localStorage.getItem(key));
            this.$emit('load',true);
            fetch('/api/user/get&iduser='+credential.id+'&tokenuser='+credential.token)
            .then(res => res.json())
            .then(res => {
                this.user = res;
                this.initializer().then(r=>this.$emit('load',false));
            });
        },
        methods: {
            async initializer() {
                this.$emit('load',true);
                var res = await fetch('/api/user/my-roles?iduser='+credential.id+'&tokenuser='+credential.token);
                this.roles = await res.json().handyRoles;
                this.$emit('load',false);
            },
            addRole(index) {
                this.$router.push({path: '/category-list/'+this.roles[index].name});
            },
            logout() {
                var credential = JSON.parse(localStorage.getItem(key));
                this.$emit('load',true);
                fetch('/api/user/logout&iduser='+credential.id+'&tokenid='+credential.token, {method: 'PUT'})
                .then(res => res.text())
                .then(res => {
                    this.user = undefined;
                    this.roles = [];
                    localStorage.removeItem(key);
                    this.$emit('load',false);
                    this.$router.replace({path: '/login'});
                });
            }
        }
});