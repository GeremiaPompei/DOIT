export default Vue.component('login', {
    template: `
    <div class='container' @submit.prevent="login">
        <form>
            <input type="text" v-model="input" id="login" placeholder="Username">
            <input type="submit" value="Login">
        </form>
    </div>
    `,
    data() {
        return {
            input: ''
        }
    },
    methods: {
        login() {
            this.$emit('login', this.input);
        }
    }
});