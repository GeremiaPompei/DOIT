export default Vue.component('signin', {
    template: 
    /*html*/`
    <div style="margin: 10px; padding: 10%; padding-top: 1%">
        <form @submit.prevent="signin">
            <div class="form-group">
                <input type="text" class="form-control" v-model="name" placeholder="Name">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" v-model="surname" placeholder="Surname">
            </div>
            <div class="form-group">
                <input type="date" class="form-control" v-model="birthDate" placeholder="Birth date">
            </div>
            <fieldset class="form-group" style="display: flex;">
                <div class="form-check" style="flex: 1">
                    <label class="form-check-label">
                        <input type="radio" value="Male" v-model="sex" class="form-check-input">
                        Male
                    </label>
                </div>
                <div class="form-check" style="flex: 1">
                    <label class="form-check-label">
                        <input type="radio" value="Female" v-model="sex" class="form-check-input">
                        Female
                    </label>
                </div>
                <div class="form-check" style="flex: 1">
                    <label class="form-check-label">
                        <input type="radio" value="Other" v-model="sex" class="form-check-input">
                        Other
                    </label>
                </div>
            </fieldset>
            <div class="form-group">
                <input type="email" class="form-control" v-model="email" placeholder="Email">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" v-model="password" placeholder="Password">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" v-model="ripetiPassword" placeholder="Ripeti password">
            </div>
            <input class="bbtn btn-secondary btn-lg btn-block" type="submit" value="Signin">
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
            if(this.name=='' || this.surname=='' || this.birthDate=='' || this.sex=='' || this.email=='' || this.password=='')
                this.$emit('push', 'Filds missed!');
            else if(this.password===this.ripetiPassword) {
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
                this.$emit('push', res);
            } else {
                alert('Le password non coincidono!')
            }
        }
    }
});