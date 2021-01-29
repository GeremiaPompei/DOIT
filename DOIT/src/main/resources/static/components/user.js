export default Vue.component('user', {
    template: `
        <div class='container'>
            <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
            <p>Name</p>
            <h3 class="el">{{user.name}}</h3>
            <p>Surname</p>
            <h3 class="el">{{user.surname}}</h3>
            <p>Birth date</p>
            <h3 class="el">{{user.birthDate}}</h3>
            <p>Sex</p>
            <h3 class="el">{{user.sex}}</h3>
            <p>Email</p>
            <h3 class="el">{{user.email}}</h3>
        </div>
        `,
        data() {
            return {
                user: {}
            }
        },
        async created() {
            this.$emit('load',true);
            this.user = await (await fetch('/api/search/user-by-id?id='+this.$route.params.id)).json();
            this.$emit('load',false);
        },
        methods: {
            back() {
                this.$router.go(-1);
            }
        }
});