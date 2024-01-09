<template>
    <v-row class="fill-height">
        <v-col>
            <v-sheet height="64">
                <v-toolbar flat>
                    <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">Today</v-btn>
                    <v-btn fab text small color="grey darken-2" @click="prev">
                        <v-icon small>mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn fab text small color="grey darken-2" @click="next">
                        <v-icon small>mdi-chevron-right</v-icon>
                    </v-btn>
                    <v-btn outlined class="mr-4" color="grey darken-2" @click="showTaskRegisterModal = true">Add Task </v-btn>
                </v-toolbar>
            </v-sheet>
            <v-sheet height="600">
                <v-calendar ref="calendar" v-model="focus" color="primary" :events="events" :type="type" @click:event="showEvent"
                @click:day="openTaskRegisterModalWithSelectDate" @click:date="showTaskRegisterModal = true" ></v-calendar>
                <!-- タスククリック時のメニュー -->
                <v-menu v-model="showTaskDetail" :close-on-content-click="false" :activator="selectedElement" offset-x>
                    <v-card color="grey lighten-4" min-width="350px" flat>
                        <v-toolbar :color="selectedEvent.color" dark>
                            <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                        </v-toolbar>
                        <v-card-text>
                            <span v-html="selectedEvent.memo"></span>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn text color="primary" @click="done(true, selectedEvent.id)">
                                Done
                            </v-btn>
                            <v-btn text color="error" @click="deleteRecord(selectedEvent.id)">
                                Delete
                            </v-btn>
                            <v-btn text color="secondary" @click="showTaskDetail = false">
                                Cancel
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-menu>
            </v-sheet>
            <!-- タスク登録時のModal -->
            <TaskRegisterModal ref="TaskRegisterModal" :showTaskRegisterModal="showTaskRegisterModal" :recordDate="selectedDate" @update:showTaskRegisterModal="showTaskRegisterModal = $event">
            </TaskRegisterModal>
        </v-col>
    </v-row>
</template>
<style>
#overlay {
    /*要素を重ねた時の順番*/
    z-index: 1;

    /*画面全体を覆う設定*/
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);

    /*画面の中央に要素を表示させる設定*/
    display: flex;
    align-items: center;
    justify-content: center;
}
#content {
    z-index: 2;
    width: 50%;
    padding: 1em;
    background: #fff;
}
</style>
<script>
import TaskRegisterModal from './TaskRegisterModal.vue'
const localHostName = process.env.VUE_APP_API_HOST_LOCAL;
export default {
    components: {
        TaskRegisterModal
    },
    data: () => ({
        selectedEvent: {},
        type: 'month',
        focus: '',
        selectedElement: null,
        showTaskDetail: false,
        events: [],
        colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
        showTaskRegisterModal: false,
        selectedDate: `${(new Date()).getFullYear()}-${((new Date()).getMonth() + 1).toString().padStart(2, '0')}-${(new Date()).getDate().toString().padStart(2, '0')}`
    }),
    mounted() {
        this.getRecords()
        this.$refs.calendar.checkChange()
    },
    methods: {
        setToday() {
            this.focus = ''
        },
        prev() {
            this.$refs.calendar.prev()
        },
        next() {
            this.$refs.calendar.next()
        },
        openTaskRegisterModalWithSelectDate({date}){
            console.log("1."+date)
            this.selectedDate = date
            this.showTaskRegisterModal = true
            this.$nextTick(() => {
                this.$refs.TaskRegisterModal.$data.recordDate = this.selectedDate;
            });
        },
        /**
         * タスククリック時にイベント詳細を表示
         */
        showEvent({ nativeEvent, event }) {
            const open = () => {
                this.selectedEvent = event
                this.selectedElement = nativeEvent.target
                requestAnimationFrame(() => requestAnimationFrame(() => this.showTaskDetail = true))
            }
            if (this.showTaskDetail) {
                this.showTaskDetail = false
                requestAnimationFrame(() => requestAnimationFrame(() => open()))
            } else {
                open()
            }
            nativeEvent.stopPropagation()
        },
        /**
         * リロード時に記録データリストを取得する
         */
        getRecords() {
            fetch(`http://${localHostName}/record?startDate=2023-1-1&endDate=2023-12-31`,
            { headers: {"X-AUTH-TOKEN" : "Bearer " + this.$store.getters.getToken }})
            .then(res => {
                if (!res.ok) {
                    throw new Error('Failed to fetch data from the server')
                }
                return res.json()
            }).then(data => {
                if (!Array.isArray(data)) {
                    throw new Error('Invalid data format received from server')
                }
                const currentDate = new Date();
                const year = currentDate.getFullYear();
                const month = String(currentDate.getMonth() + 1).padStart(2, '0');
                const day = String(currentDate.getDate()).padStart(2, '0');
                const formattedCurrentDate = new Date(`${year}-${month}-${day}`)
                return data.map((item) => {
                    const recordDate = new Date(item.recordDate)
                    console.log(recordDate + ","+formattedCurrentDate)
                        // if(!(recordDate < formattedCurrentDate & item.isDone == false)){
                            this.events.push(
                                {
                                    id: item.id,
                                    name: `${item.recordContentName}:${item.requiredTime}h`,
                                    start: new Date(item.recordDate),
                                    color: this.colors[this.rnd(0, this.colors.length - 1)],
                                    memo: item.memo,
                                    isDone: item.isDone,
                                }
                                ) 
                        // }
                    })
                }).catch(error => {
                    console.error(error)
                });
        },
        /**
         * 記録を削除する
         * @param {*} recordId 記録id
         */
        deleteRecord(recordId) {
            if (!window.confirm("タスクを削除します。よろしいですか？")) { return }
            fetch(`http://${localHostName}/record/${encodeURIComponent(recordId)}`,
            { method: "DELETE", headers: {"X-AUTH-TOKEN" : "Bearer " + this.$store.getters.getToken }})
            .then(res => {
                if (!res.ok) {
                    throw new Error("Failed to delete record from the server");
                }
            }).catch(e => {
                console.error(e);
            }).finally(() => {
                this.showTaskDetail = false
            })
        },
        /**
        * タスクの完了状況を更新する
        */
        done(isDone, recordId) {
            const params = { isDone: isDone, recordId: recordId };
            const query = new URLSearchParams(params);
            fetch(`http://${localHostName}/record/complete?${query}`,
            { method: "POST", headers: {"X-AUTH-TOKEN" : "Bearer " + this.$store.getters.getToken }})
            .then(res => {
                if (res.status != "200") {
                    throw new Error("Failed register record.statusText=" + res.status)
                }
            }).catch(error => {
                console.error(error)
            }).finally( this.showTaskDetail = false )
        },
        rnd(a, b) {
            return Math.floor((b - a + 1) * Math.random()) + a
        },
    },
}
</script>