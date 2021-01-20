export default Vue.component('designer', {
    template: `
    <div class='container'>
        <role :role="role"></role>
    </div>
    `,
    data() {
        return {
            role: undefined
        }
    },
    created() {
        this.$emit('load',true);
        fetch('/api/role/'+this.$route.params.user+'/designer').then(res=>res.json()).then(res=>{
            this.role = res;
            this.$emit('load',false);
        });
    },
});