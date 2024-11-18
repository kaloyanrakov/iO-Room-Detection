import '../assets/css/layout.css';
import {Outlet} from "react-router-dom";

function Layout({sidebarContent, rightSideContent}) {
    return (
        <div>
            <div className="container">
                <div className="sidebar">
                    {sidebarContent}
                    <div className="current-time">
                        <p>9:00</p>
                        <p>Tuesday</p>
                        <p>18/11/2024</p>
                    </div>
                </div>

                <div className="right-side">
                    {rightSideContent || <Outlet />}
                </div>
            </div>
        </div>
    )
}

export default Layout;