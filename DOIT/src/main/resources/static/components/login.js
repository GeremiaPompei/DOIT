export default Vue.component('login', {
    template: `
    <div class='container' @submit.prevent="login">
        <form>
            <input type="text" v-model="input.email" placeholder="Email">
            <input type="password" v-model="input.password" placeholder="Password">
            <input type="submit" value="Login">
        </form>
    </div>
    `,
    data() {
        return {
            input: {}
        }
    },
    methods: {
        login() {
            this.$emit('load',true);
            fetch('/api/user/login', {method: 'POST', body: this.input})
            .then(res => this.$emit('load',false));
        }
    }
});