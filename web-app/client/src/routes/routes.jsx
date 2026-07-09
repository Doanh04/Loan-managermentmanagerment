import RegisterControler from "../feature/Register/Controler/RegisterControler";
import LoginLayout from "../Layout/LoginLayout";

export const Routers = [
    {
        path: "/login",
        element: <LoginLayout />,

    },
    {
        path: "/register",
        element: <RegisterControler />
    },
]