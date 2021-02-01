export default Vue.component('user-main', {
    template: 
    /*html*/`
    <div style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;" >
        <div class='card mb-3'>
            <h3 class="card-header" style="text-align: center">{{user.name}} {{user.surname}}</h3>
            <div class="card-body">
                <h5 class="card-title">Birth Date</h5>
                <h6 class="card-subtitle text-muted">{{user.birthDate}}</h6>
            </div>
            <div class="card-body">
                <h5 class="card-title">Sex</h5>
                <h6 class="card-subtitle text-muted">{{user.sex}}</h6>
            </div>
            <div class="card-body">
                <h5 class="card-title">Email</h5>
                <h6 class="card-subtitle text-muted">{{user.email}}</h6>
            </div>
            <div class="card-body">
                <h5 class="card-title">Add roles</h5>
                <div class="form-group">
                    <select @change="onChange($event)" class="form-control">
                        <option key="-1" value="-1">---</option>
                        <option v-for="(category, index) in categories" :value="index" :key="index">
                            {{category.name}}
                        </option>
                    </select>
                    <ul>
                        <li v-for="(role, index) in roles" :key="index" @click="addRole(index)" class="el" style="list-style-type: none; padding-top: 15px">
                            <button class="btn btn-outline-primary" style="width: 100%">{{role}}</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="card-body">
                <h5 class="card-title">Contact Us</h5>
                <form @submit.prevent="send()">
                    <input type="text" placeholder="Inserisci messaggio..." v-model="message" class="form-control"></input>
                    <input type="submit" value="Send" class="btn btn-outline-primary" style="margin-top: 10px; width: 100%;">
                </form>
            </div>
        </div>
        <button class="btn btn-outline-primary" style="width: 100%" @click="logout">Logout</button>
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
                    this.$emit('push',"Seleziona una categoria!");
                } else {
                    this.$emit('load',true);
                    var credential = JSON.parse(localStorage.getItem(key));
                    var res = await (await fetch('/api/user/add-role?iduser='+credential.id+'&tokenuser='+credential.token+
                        '&idrole='+this.roles[index]+'&idcategory='+this.categories[this.categoryIndex].name, {method: 'POST'})).text();
                    this.$emit('load',false);
                    this.$emit('push', res);
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
                this.$emit('push', res);
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
                    this.$emit('push', res);
                } else {
                    this.$emit('push', 'Fild missed!');
                }
            }
        }
});