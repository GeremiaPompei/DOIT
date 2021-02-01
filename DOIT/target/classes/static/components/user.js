export default Vue.component('user', {
    template: 
    /*html*/`
    <div style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;" >
        <div class='card mb-3'>
            <h3 class="card-header" style="text-align: center">{{user.name}} {{user.surname}}</h3>
            <div class="card-body">
                <h5 class="card-title">Birth Date</h5>
                <h6 class="card-subtitle text-muted">{{user.birthDate}}</h6>
            </div>
            <div class="card-body">
                <h5 class="card-title">Sex</h5>
                <h6 class="card-subtitle text-muted">{{user.sex}}</h6>
            </div>
            <div class="card-body">
                <h5 class="card-title">Email</h5>
                <h6 class="card-subtitle text-muted">{{user.email}}</h6>
            </div>
        </div>
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