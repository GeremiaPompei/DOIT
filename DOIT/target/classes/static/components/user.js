export default Vue.component('user', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
    <div>
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
    </div>
    <div class="card border-info mb-3" style="margin-top: 10px">
          <div class="card-header" style="text-align: center">{{user.name}} {{user.surname}}</div>
          <div class="card-body">
            <h5 class="card-title">Birth Date</h5>
            <p class="card-subtitle text-muted">{{user.birthDate}}</p>
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