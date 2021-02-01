export default Vue.component('login', {
    template: 
    /*html*/`
    <div style="margin: 10px; padding: 10%; padding-top: 1%">
        <form @submit.prevent="login">
            <div class="form-group">
                <input type="text" class="form-control" v-model="email" placeholder="Email">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" v-model="password" placeholder="Password">
            </div>
            <div class="form-group">
                <input type="submit" value="Login">
            </div>
        </form>
    </div>
    `,
    data() {
        return {
            email: '',
            password: ''
        }
    },
    methods: {
        async login() {
            if(this.email=='' || this.password=='')
                this.$emit('push', 'Filds missed!');
            else {
                this.$emit('load',true);
                try {
                    var res = await (await fetch('/api/user/login?email='+this.email+'&password='+md5(this.password), {method: 'PUT'})).json();
                    if(res) {
                        var stringify = JSON.stringify(res);
                        localStorage.setItem(key, stringify);
                        this.$router.replace({path: '/user-main'});
                    }
                } catch(e) {
                    this.$emit('push', e);
                }
                this.email = '';
                this.password = '';
                this.$emit('load',false);
            }
        }
    }
});