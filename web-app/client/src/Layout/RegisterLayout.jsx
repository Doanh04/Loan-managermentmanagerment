import "../Styles/Register.scss"
import girl from "../assets/Images/Girl-removebg-preview.png"
import logo from "../assets/Images/Logo-removebg-preview.png"
import {CreditCardOutlined, LockOutlined, ThunderboltOutlined } from '@ant-design/icons';

function RegisterLayout() {
    return(
        <>
            <div className = "registerLayout">
                <div className = "registerLayout__content">
                    <div className = "registerLayout__content--image">
                        <img src={girl} alt="Girl" />
                    </div>
                    <div className = "registerLayout__content--title">
                        <div className = "registerLayout__content--title-logo">
                            <img src={logo} alt="Logo" />
                            <div className = "registerLayout__content--title-logo-text">
                                <nav>D </nav>
                                <nav>COST</nav>
                            </div>
                        </div>
                        <div className = "registerLayout__content--title-text">
                            <h1>VAY VỐN TINH HOA - TIỆN LỢI & AN TOÀN </h1>
                            <p>Giải pháp tài chính linh hoạt cho mọi nhu cầu</p>
                        </div>
                        <div className = "registerLayout__content--title-icons">
                            <nav>
                                <CreditCardOutlined/>
                                <p>Lãi suất ưu đãi chỉ tử 1.2%/tháng</p>
                            </nav>
                            <nav>
                                <ThunderboltOutlined/>
                                <p>Duyệt hồ sơ nhanh chóng trong 15 phút</p>
                            </nav>
                            <nav>
                                <LockOutlined/>
                                <p>Bảo mật thông tin khách hàng tuyệt đối</p>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default RegisterLayout;