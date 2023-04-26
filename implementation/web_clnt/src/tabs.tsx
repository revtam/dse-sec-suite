import 'assets/main.css'
import c from 'classnames'
import React, {useState} from 'react'
import {Benchmark} from "@/benchmark";
import {Main} from "@/main";

export interface TabsProps {
    className?: string
    onClick?: (e: React.MouseEvent<HTMLDivElement>) => void
}

export const Tabs: React.FunctionComponent<TabsProps> = ({className, onClick}: TabsProps) => {
    const [selectedTab, setSeledtedTab] = useState<'msbill' | 'bench'>('bench')

    const handleToggleTab = () => {
        setSeledtedTab(selectedTab === 'msbill' ? 'bench' : 'msbill')
    }

    return (
        <div className={c('tabs', className)} onClick={onClick}>
            <button onClick={handleToggleTab}>Switch to {selectedTab === 'msbill' ? 'Benchmark' : 'Normal'}</button>
            <div className='tabs-wrapper'>
                {
                    selectedTab === 'msbill' ? <Main/> : <Benchmark/>
                }
            </div>
        </div>
    )
}
