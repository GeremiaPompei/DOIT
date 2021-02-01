export default Vue.component('role', {
    props: {
        role: {
            type: String,
            required: true
        }
    },
    template: 
    /*html*/`
    <div class=''>
        <ul style="list-style-type: none;">
            <li v-for="(el, index) in list" :key="index" style="padding-top: 10px">
                <button @click="go(index)" class="btn btn-outline-primary" style="width: 100%">
                    {{el.name}}
                </button>
            </li >
            <li style="padding-top: 10px">
                <button @click="removeRole()" class="btn btn-outline-primary" style="width: 100%">
                    Remove role
                </button>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            list: [
                {name: 'List projects', path: {path: '/list-projects/'+this.role}},
                {name: 'History', path: {path: '/history/'+this.role}},
                {name: 'List categories', path: {path: '/list-categories/'+this.role}},
                {name: 'List notifications', path: {path: '/list-notifications/'+this.role}},
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
            if(res=="success")
                this.$router.replace({path: '/user-main'});
            this.$emit('push', res);
        }
    }
});