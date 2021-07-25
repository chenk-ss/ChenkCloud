var home = new Vue({
    el: '#app',
    data: {},
    methods: {
        start: function () {
        },

        uploadCos: function () {
            var _self = this;
            var file = document.getElementById("uploadFile").files[0];
            var size = file.size;
            if (size > 100 * 1024 * 1024) {
                alert("请不要上传大于100M的文件");
                return;
            }
            var fileName = file.name;
            var fileType = fileName.substring(fileName.lastIndexOf("\.") + 1);
            var newName = new Date().getTime() + '-' + _self.getUuid() + '.' + fileType;
            var boo = false;
            var postData = {
                'fileName': newName,
                'originFileName': fileName,
                'url': newName,
                'size': size
            };
            cos.putObject({
                    Bucket: BUCKET_NAME, // 桶名-APPID  必须
                    Region: REGION, //区域 必须
                    Key: newName, //文件名必须
                    StorageClass: 'STANDARD',
                    Body: file, // 上传文件对象
                    onTaskStart: function (progressData) {
                        console.log(JSON.stringify(progressData));//上传成功的返回值
                        boo = true;
                    }
                },
                function (err, data) {
                    console.log(err || data);//上传失败的返回值
                    if (data.statusCode === 200 && boo) {
                        postAjax(HOST_DB_INSERT, JSON.stringify(postData), function (ret, err) {
                            if (ret) {
                                alert(ret);
                            }
                        });
                    }
                }
            )

        },
        getUuid: function () {
            function S4() {
                return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
            }

            return (S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4());
        },
        queryList: function () {
            window.location.href = HOST_LIST_HTML;
        }
    },
    created() {
        this.start();
    }
})