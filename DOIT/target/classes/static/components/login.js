export default Vue.component('login', {
    template: `
    <div class='container'>
        <form @submit.prevent="login">
            <input type="text" v-model="email" placeholder="Email">
            <input type="password" v-model="password" placeholder="Password">
            <input type="submit" value="Login">
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
        login() {
            this.$emit('load',true);
            fetch('/api/user/login?email='+this.email+'&password='+md5(this.password), {method: 'PUT'})
            .then(res => res.json())
            .then(res => {
                if(res) {
                    var stringify = JSON.stringify(res);
                    localStorage.setItem(key, stringify);
                    this.$router.replace({path: '/user'});
                }
                this.email = '';
                this.password = '';
                this.$emit('load',false);
            });
        }
    }
});