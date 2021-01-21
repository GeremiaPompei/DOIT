export default Vue.component('login', {
    template: `
    <div class='container' @submit.prevent="login">
        <form>
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
            fetch('/api/user/login', {method: 'POST', body: {
                'email': this.email,
                'password': this.password
            }, headers: {'Content-Type': 'application/json'}})
            .then(res => res.json())
            .then(res => {
                this.$emit('load',false); 
                alert(res.message);
            });
        }
    }
});