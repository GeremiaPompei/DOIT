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
            fetch('/api/user/login', {
                method: 'POST', 
                body: this.email + " " + this.password , 
                headers: {'Content-Type': 'application/json'}})
            .then(res => res.text())
            .then(res => {
                this.$emit('load',false);
                localStorage.setItem(key, res);
                this.$router.replace({path: '/user'});
            });
        }
    }
});