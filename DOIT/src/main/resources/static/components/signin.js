export default Vue.component('signin', {
    template: `
    <div class='container' @submit.prevent="signin">
        <form>
            <input type="text" v-model="input.name" placeholder="Name">
            <input type="text" v-model="input.surname" placeholder="Surname">
            <input type="text" v-model="input.birthDate" placeholder="Birth date">
            <input type="text" v-model="input.sex" placeholder="Sex">
            <input type="text" v-model="input.email" placeholder="Email">
            <input type="password" v-model="input.password" placeholder="Password">
            <input type="password" v-model="ripetiPassword" placeholder="Ripeti password">
            <input type="submit" value="Signin">
        </form>
    </div>
    `,
    data() {
        return {
            ripetiPassword: '',
            input: {}
        }
    },
    methods: {
        signin() {
            this.$emit('load',true);
            if(this.ripetiPassword==input.password)
                fetch('/api/user/signin', {method: 'POST', body: this.input})
                .then(res => this.$emit('load',false));
        }
    }
});