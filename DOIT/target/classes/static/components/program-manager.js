export default Vue.component('program-manager', {
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
        fetch('/api/role/'+this.$route.params.user+'/program-manager').then(res=>res.json()).then(res=>{
            this.role = res;
            this.$emit('load',false);
        });
    },
});