<template>
    <div id="overlay" v-show="showTaskRegisterModal">
        <v-card>
            <v-card-title>
                <span class="text-h5">Add Task</span>
            </v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                        <v-select
                            v-model="recordContentName"
                            :items="junres"
                            label="junres"
                            item-text="recordContentName"
                            required
                        ></v-select>
                        </v-col>
                        <v-col cols="12">
                        <v-menu
                            ref="menu"
                            v-model="showcalendar"
                            :close-on-content-click="false"
                            :return-value.sync="formData.recordDate"
                            transition="scale-transition"
                            offset-y
                            min-width="auto"
                        >
                            <template v-slot:activator="{ on, attrs }">
                            <v-text-field
                                v-model="formData.recordDate"
                                label="Picker in menu"
                                prepend-icon="mdi-calendar"
                                readonly
                                v-bind="attrs"
                                v-on="on"
                            ></v-text-field>
                            </template>
                            <v-date-picker
                            v-model="formData.recordDate"
                            no-title
                            scrollable
                            >
                            <v-spacer></v-spacer>
                            <v-btn
                                text
                                color="primary"
                                @click="showcalendar = false"
                            >
                                Cancel
                            </v-btn>
                            <v-btn
                                text
                                color="primary"
                                @click="$refs.menu.save(formData.recordDate)"
                            >
                                OK
                            </v-btn>
                            </v-date-picker>
                        </v-menu>
                        </v-col>
                        <v-col cols="12">
                        <v-select
                            v-model="formData.requiredTime"
                            :items="requiredTImeList"
                            label="Required Time"
                            item-text="recordContentName"
                            required
                        ></v-select>
                        </v-col>
                        <v-col cols="12">
                            <v-text-field
                                v-model=formData.memo
                                label="memo"
                            ></v-text-field>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                color="blue-darken-1"
                variant="text"
                @click="computedShowTaskRegisterModal = false"
                >
                Close
                </v-btn>
                <v-btn
                color="blue-darken-1"
                variant="text"
                @click="saveForm()"
                >
                Save
                </v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>
<script>
    const localHostName = process.env.VUE_APP_API_HOST_LOCAL;
    export default{
        data: () => ({
            
            showcalendar: false,
            junres: [],
            requiredTImeList: [1,2,3,4,5,6,7,8,9,10],
            recordContentName: '',
            formData:{
                recordContentId: '',
                recordDate: '',
                requiredTime: '',
                memo: '',
                createUserId: '1'
            }
        }),
        props: {
            showTaskRegisterModal: {
                type: Boolean,
                default: false
            },
            recordDate: {
                type: String,
                default: ""
            }
        },
        computed: {
            // showTaskRegisterModalの変更を親コンポーネントに通知する
            computedShowTaskRegisterModal: {
            get() {
                return this.showTaskRegisterModal;
            },
            set(value) {
                this.$emit('update:showTaskRegisterModal', value);
            }
            }
        },
        watch: {
            //propsの変更をウォッチする
            recordDate: function(oldVal){
                this.formData.recordDate = oldVal
            }
        },
        mounted() {
            this.getRecordContent()
        },
        methods:{
            /**
             * ジャンル一覧を取得する
             */
            getRecordContent(){
                fetch(`http://${localHostName}/recordContents`,{ 
                    headers: {"X-AUTH-TOKEN" : "Bearer " + this.$store.getters.getToken}
                }).then(res => res.json()
                ).then(data => {
                    return data.map(
                        item => {
                            return {
                                id: item.id,
                                recordContentName: item.recordContentName
                            }
                        }
                    )
                }).then(
                    junres => {
                        this.junres = junres
                        console.log(junres)
                    }
                ).catch(
                    e => console.error(e)
                )
            },
            /**
             * 記録を新規登録する
             */
            saveForm() {
                this.formData.recordContentId = this.junres.find(recordContent => {
                    return this.recordContentName === recordContent.recordContentName;
                }).id;
                this.formData.createUserId = this.$store.state.loginUserId
                console.log(JSON.stringify(this.formData))
                fetch(`http://${localHostName}/record`, {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        "X-AUTH-TOKEN" : "Bearer " + this.$store.getters.getToken 
                    },
                    body: JSON.stringify(this.formData),
                }).then(res => {
                    if(res.status !== 201){
                        throw new Error("Failed register record.statusText="+res.status)
                    }
                }).catch(
                    e => console.error(e)
                )
                this.computedShowTaskRegisterModal = false
            },
        }
    }
</script>
