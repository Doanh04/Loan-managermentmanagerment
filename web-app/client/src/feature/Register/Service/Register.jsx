import HttpClient from '../../../Config/HttpClient';


const USER_BASE_URL = '/user';

const registerUser = async (userData) => {
    try {
        const response = await HttpClient.post(`${USER_BASE_URL}/create-user`, {
            userName: userData.userName,
            passWord: userData.passWord,
            phoneNumber: userData.phoneNumber,
            email_verified: userData.email_verified,
        });
        const data = response.data;

        if(data.code === 1000){
            return data;
        }
        if(data.code === 1018){
            throw new Error('Tài khoản đã tồn tại');
        }
        if(data.code === 1017){
            throw new Error('Tài khoản đã tồn tại');
        }
        else{
            throw new Error('Dữ liệu không hợp lệ');
        }
    }
    catch (error) {
        console.error('Lỗi khi đăng ký người dùng:', error);
        throw new error;
    }
}

const verifyUser = async (userData) => {
    try {
        const response = await HttpClient.post(`${USER_BASE_URL}/verify-otp`, {
            oTP: userData.oTP,
            phoneNumber: userData.phoneNumber
        });
        const data = response.data;

        if(data.code === 1000){
            return data;
        }
        if(data.code === 1024){
            throw new Error('Mã OTP đã hết hạn');
        }
        if(data.code === 1023){
            throw new Error('Mã OTP không hợp lệ');
        }
        if(data.code === 1021){
            throw new Error('Số điện thoại không tồn tại');
        }
        else{
            throw new Error('Mã OTP không hợp lệ');
        }
    }
    catch (error) {
        console.error('Lỗi khi xác thực người dùng:', error);
        throw new error;
    }
}
export { registerUser, verifyUser };