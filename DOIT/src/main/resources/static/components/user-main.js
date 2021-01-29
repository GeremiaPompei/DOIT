export default Vue.component('user-main', {
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
            <p>Add roles</p>
            <div>
                <select @change="onChange($event)">
                    <option key="-1" value="-1">---</option>
                    <option v-for="(category, index) in categories" :value="index" :key="index">
                        {{category.name}}
                    </option>
                </select>
                <ul>
                    <li v-for="(role, index) in roles" :key="index" @click="addRole(index)" class="el">
                        <button>{{role}}</button>
                    </li>
                </ul>
            </div>
            <div>
                <p>Contact us:</p>
                <form @submit.prevent="send()">
                    <input type="text" placeholder="Inserisci messaggio..." v-model="message"></input>
                    <input type="submit" value="Send">
                </form>
            </div>
            <button @click="logout">Logout</button>
        </div>
        `,
        data() {
            return {
                user: {},
                roles: [],
                categories: [],
                categoryIndex: -1,
                message: ''
            }
        },
        async created() {
            this.$emit('load',true);
            this.roles = [];
            var credential = JSON.parse(localStorage.getItem(key));
            this.user = await (await fetch('/api/user/get?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            this.categories = await (await fetch('/api/search/categories')).json();
            var roles = await (await fetch('/api/user/handy-roles-type?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            var myroles = await (await fetch('/api/user/my-roles-type?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            roles.forEach((role) => {
                if(!myroles.includes(role)) {
                    this.roles.push(role);
                }
            });
            this.$emit('load',false);
        },
        methods: {
            async addRole(index) {
                if(this.categoryIndex<0) {
                    alert("Seleziona una categoria!");
                } else {
                    this.$emit('load',true);
                    var credential = JSON.parse(localStorage.getItem(key));
                    var res = await (await fetch('/api/user/add-role?iduser='+credential.id+'&tokenuser='+credential.token+
                        '&idrole='+this.roles[index]+'&idcategory='+this.categories[this.categoryIndex].name, {method: 'POST'})).text();
                    this.$emit('load',false);
                    alert(res);
                    this.$router.replace({path: '/'+this.roles[index]});
                }
            },
            async logout() {
                var credential = JSON.parse(localStorage.getItem(key));
                this.$emit('load',true);
                var res = await (await fetch('/api/user/logout?iduser='+credential.id+'&tokenuser='+credential.token, {method: 'PUT'})).text();
                this.user = undefined;
                this.roles = [];
                localStorage.removeItem(key);
                this.$emit('load',false);
                alert(res);
                this.$router.replace({path: '/login'});
            },
            onChange(event) {
                this.categoryIndex = event.target.value;
            },
            async send() {
                if(this.message!='') {
                    var credential = JSON.parse(localStorage.getItem(key));
                    this.$emit('load',true);
                    var res = await (await fetch('/api/user/send-email?iduser='+credential.id+'&tokenuser='+credential.token+'&message='+this.message, {method: 'POST'})).text();
                    this.$emit('load',false);
                    this.message = '';
                    alert(res);
                } else {
                    alert("Inserisci messaggio!");
                }
            }
        }
});