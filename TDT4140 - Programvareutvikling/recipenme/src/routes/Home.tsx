import FrontPageTabs from "../components/molecules/FrontPageTabs";
import MainBody from "../tempComponents/MainBody"
//import NewMainBody from "../tempComponents/NewMainBody"

export default function Home() {
    return (
        <div className='parent'>
            <div className='topBar'>
            <FrontPageTabs></FrontPageTabs>
            {/* <MainBody /> */}
            </div>
            <div className='feed'>
            </div>
        </div>
    );
}