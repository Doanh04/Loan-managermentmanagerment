import "../Styles/Register.scss";
import girl from "../assets/Images/Girl-removebg-preview.png";
import logo from "../assets/Images/Logo-removebg-preview.png";
import {
  CreditCardOutlined,
  LockOutlined,
  ThunderboltOutlined,
} from "@ant-design/icons";
import { Row, Col, Form, Input, Button } from "antd";

function RegisterLayout() {
  return (
    <>
      <div className="registerLayout">
        <div className="registerLayout__content">
          <div className="registerLayout__content--image">
            <img src={girl} alt="Girl" />
          </div>
          <div className="mobile-logo">
                <img src={logo} alt="Logo" />
                <span>D-COST</span>
          </div>
          <Row justify="space-between" align="stretch">
            <Col span={10} className="registerLayout__content--title">
              <div style={{ margin: "50px" }}>
                <div className="registerLayout__content--title-logo">
                  <img src={logo} alt="Logo" />
                    <div className="registerLayout__content--title-logo-text">
                        <nav>D-COST</nav>
                    </div>
                </div>
                <div className="registerLayout__content--title-text">
                  <h1>VAY VỐN SIÊU NHANH - TIỆN LỢI & AN TOÀN </h1>
                  <p>Giải pháp tài chính linh hoạt cho mọi nhu cầu</p>
                </div>
                <div className="registerLayout__content--title-icons">
                  <nav>
                    <CreditCardOutlined />
                    <p>Lãi suất ưu đãi chỉ tử 1.2%/tháng</p>
                  </nav>
                  <nav>
                    <ThunderboltOutlined />
                    <p>Duyệt hồ sơ nhanh chóng trong 15 phút</p>
                  </nav>
                  <nav>
                    <LockOutlined />
                    <p>Bảo mật thông tin khách hàng tuyệt đối</p>
                  </nav>
                </div>
              </div>
            </Col>
            <Col span={10} className="registerLayout__content--forms">
                <div className = "title">
                  <h1>Đăng ký tài khoản</h1>
                  <p>(Sau khi đăng ký, bạn sẽ nhận được mã xác nhận qua số điện thoại.)</p>
                </div>
                <Form onFinish={(values) => console.log(values)} >
                    <p>Tên đăng nhập</p>
                    <Form.Item
                        name="userName"
                        rules={[
                            { required: true, message: "Vui lòng nhập tên đăng nhập" },
                            {min: 10, message: "Tên đăng nhập phải có ít nhất 10 ký tự"},
                        ]}>
                        <Input placeholder="Tên đăng nhập" />
                    </Form.Item>
                    <p>Số điện thoại</p>
                    <Form.Item
                        name="phoneNumber"
                        rules={[
                            { required: true, message: "Vui lòng nhập số điện thoại" },
                            { pattern: /^[0-9]{10}$/, message: "Số điện thoại không hợp lệ" },
                        ]}>
                        <Input placeholder="Số điện thoại" />
                    </Form.Item>
                    <p>Mật khẩu</p>
                    <Form.Item
                        name="passWord"
                        rules={[
                            { required: true, message: "Vui lòng nhập mật khẩu" },
                            {min: 8, message: "Mật khẩu phải có ít nhất 8 ký tự"},
                        ]}>
                        <Input.Password placeholder="Mật khẩu" />
                    </Form.Item>
                    <p>Xác nhận mật khẩu</p>
                    <Form.Item
                        name="confirmPassword"
                        dependencies={['passWord']}
                        rules={[
                            { required: true, message: "Vui lòng xác nhận mật khẩu" },
                            ({ getFieldValue }) => ({
                                validator(_, value) {
                                    if (!value || getFieldValue('passWord') === value) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error('Mật khẩu xác nhận không khớp'));
                                },
                            }),
                        ]}>
                        <Input.Password placeholder="Xác nhận mật khẩu" />
                    </Form.Item>
                    <p>Email</p>
                    <Form.Item
                        name="email_verified"
                        rules={[
                            { required: true, message: "Vui lòng nhập email" },
                            { type: 'email', message: "Email không hợp lệ" },
                        ]}>
                        <Input placeholder="Email" />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" className="registerLayout__content--forms-button">
                            Tiếp theo 
                        </Button>
                    </Form.Item>
                </Form>
                <div>
                    <p>Đã có tài khoản? <a href="/login">Đăng nhập ngay</a></p>
                </div>
            </Col>
          </Row>
        </div>
      </div>
    </>
  );
}

export default RegisterLayout;
