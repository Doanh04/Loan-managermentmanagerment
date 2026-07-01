import RegisterLayout from "../Layout/RegisterLayout";
import LoginLayout from "../Layout/LoginLayout";

export const Routers = [
    {
        path: "/login",
        element: <LoginLayout />,

    },
    {
        path: "/register",
        element: <RegisterLayout />
    },
]