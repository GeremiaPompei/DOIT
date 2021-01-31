export default Vue.component('signin', {
    template: `
    <div>
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
        async signin() {
            if(this.password===this.ripetiPassword) {
                this.$emit('load',true);
                var res = await (await fetch('/api/user/signin?name='+this.name+'&surname='+this.surname+'&birthdate='+this.birthDate+
                '&sex='+this.sex+'&email='+this.email+'&password='+md5(this.password), {method: 'POST'})).text();
                this.name = '';
                this.surname= '';
                this.birthDate= '';
                this.sex= '';
                this.email= '';
                this.password= '';
                this.ripetiPassword= '';
                this.$emit('load',false);
                alert(res);
            } else {
                alert('Le password non coincidono!')
            }
        }
    }
});