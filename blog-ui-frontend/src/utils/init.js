export default {
    setLocalUserCommentInfo: ({ userNickname, userEmail, qqNum }) => {
        localStorage.setItem("commentUserInfo", JSON.stringify({ userNickname, userEmail, qqNum }));
    },

    getLocalUserCommentInfo: () => {
        let commentUserInfo = localStorage.getItem("commentUserInfo");
        return JSON.parse(commentUserInfo);
    },

    initCommentInfo(form) {
        let commentUser = this.getLocalUserCommentInfo();
        if (commentUser) {
            form.userNickname = commentUser.userNickname;
            form.userEmail = commentUser.userEmail;
            form.qqNum = commentUser.qqNum;
        }
    },
}