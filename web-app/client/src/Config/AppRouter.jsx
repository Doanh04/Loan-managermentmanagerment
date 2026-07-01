import { useRoutes } from 'react-router-dom';
import { Routers } from '../routes/routes';

const AppRouter = () => {
try {
        const elements = useRoutes(Routers);
        console.log("Router khởi tạo thành công!");
        return elements;
    } catch (e) {
        console.error("LỖI TẠI ĐÂY:", e);
        return <div>Lỗi Router: {e.message}</div>;
    }
}; 
export default AppRouter;