export default Vue.component('role', {
    props: {
        role: {
            type: String,
            required: true
        }
    },
    template: `
    <div class='container'>
        <ul>
            <li v-for="(el, index) in list" :key="index">
                <button @click="go(index)">
                    {{el.name}}
                </button>
            </li>
            <button @click="removeRole()">
                Remove role
            </button>
        </ul>
    </div>
    `,
    data() {
        return {
            list: [
                {name: 'List projects', path: {path: '/list-projects/'+this.role}},
                {name: 'History', path: {path: '/history/'+this.role}},
                {name: 'List categories', path: {path: '/list-categories/'+this.role}},
                {name: 'Manage category', path: {path: '/manage-category/'+this.role}}
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        },
        async removeRole() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/user/remove-role?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role, {method: "DELETE"})).text();
            this.$emit('load',false);
            alert(res);
            this.$router.replace({path: '/user-main'});
        }
    }
});