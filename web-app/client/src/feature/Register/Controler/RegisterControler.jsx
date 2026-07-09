import RegisterLayout from "../../../Layout/RegisterLayout";
import { useState } from "react";
import { message } from "antd";
import { registerUser } from "../Service/Register";
import { useNavigate } from "react-router-dom";

function RegisterControler() {
    const [loading, setLoading] = useState(false);
    const [formValues, setFormValues] = useState({});
    const [messageError, contextHolder] = message.useMessage();

    const navigate = useNavigate();

    const handleChange = (changedValues, allValues) => {
        setFormValues(allValues);
    }

    const handleSubmit = async (values) => {
        setLoading(true);
        try{
            const response = await registerUser(values);
            if(response.code === 1000){
                message.success("Đăng ký thành công! Vui lòng kiểm tra số điện thoại để nhận mã xác nhận.");
                navigate("/verify", { state: { phoneNumber: values.phoneNumber } });
                return;
            }
            if(response.code === 1018 || response.code === 1017){
                messageError.error('Tài khoản đã tồn tại');
            }
            else{
                messageError.error('Dữ liệu không hợp lệ');
            }
            setLoading(false);
        }
        catch (error) {
            console.error('Lỗi khi đăng ký người dùng:', error);
            messageError.error(error.message);
        }
        finally{
            setLoading(false);
        }
    }

      const handleReset = () => {
        setFormValues({});
    };

    const formProps = {
        loading,
        formValues,
        handleChange,
        handleSubmit,
        handleReset
    };
    return (
        <>
            {contextHolder}
            <RegisterLayout {...formProps}/>
        </>
    )
}

export default RegisterControler;