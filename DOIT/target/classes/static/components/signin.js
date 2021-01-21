export default Vue.component('signin', {
    template: `
    <div class='container'>
        <form @submit.prevent="signin">
            <input type="text" v-model="name" placeholder="Name">
            <input type="text" v-model="surname" placeholder="Surname">
            <input type="text" v-model="birthDate" placeholder="Birth date">
            <input type="text" v-model="sex" placeholder="Sex">
            <input type="text" v-model="email" placeholder="Email">
            <input type="password" v-model="password" placeholder="Password">
            <input type="password" v-model="ripetiPassword" placeholder="Ripeti password">
            <input type="submit" value="Signin">
        </form>
    </div>
    `,
    data() {
        return {
            name: '',
            surname: '',
            birthDate: '',
            sex: '',
            email: '',
            password: '',
            ripetiPassword: ''
        }
    },
    methods: {
        signin() {
            this.$emit('load',true);
            fetch('/api/user/signin', {
                method: 'POST', 
                body: this.name + " " + this.surname + " " + this.birthDate + " " + this.sex + " " + this.email + " " + this.password, 
                headers: {'Content-Type': 'application/json'}})
            .then(res => res.text())
            .then(res => {
                this.$emit('load',false);
            });
        }
    }
});