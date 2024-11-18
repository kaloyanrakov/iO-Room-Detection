import Layout from '../Layout';

function AllRoomsPage() {
    const sidebarContent = (
        <p>This is the content on the left</p>
    );

    const mainContent = (
        <p>This is the content on the right</p>
    );
    return(
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent}/>
    );
}

export default AllRoomsPage;