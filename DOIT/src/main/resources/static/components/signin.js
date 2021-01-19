export default Vue.component('signin', {
    template: `
    <div class='container' @submit.prevent="signin">
        <form>
            <input type="text" v-model="input.name" placeholder="Name">
            <input type="text" v-model="input.surname" placeholder="Surname">
            <input type="text" v-model="input.birthDate" placeholder="Birth date">
            <input type="text" v-model="input.sex" placeholder="Sex">
            <input type="submit" value="Signin">
        </form>
    </div>
    `,
    data() {
        return {
            input: {}
        }
    },
    methods: {
        signin() {
            this.$emit('signin', this.input);
        }
    }
});