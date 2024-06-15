import {
  desktopContentAreaWidth,
  desktopContentAreaPadding,
} from '../../config/constants';

interface ContentAreaProps {
  children: React.ReactNode;
  width?: number;
  shrink?: boolean;
  padding?: number;
  fitHeight?: boolean;
  style?: React.CSSProperties;
}

function ContentArea({
  children,
  width = desktopContentAreaWidth,
  shrink = true,
  fitHeight = false,
  padding = desktopContentAreaPadding,
  style,
}: ContentAreaProps) {
  return (
    <div
      style={{
        width: '100%',
        minWidth: shrink || !width ? '0px' : `${width + padding * 2}px`,
        display: 'flex',
        height: fitHeight ? '100%' : 'auto',
        flexDirection: 'row',
        justifyContent: 'center',
        paddingLeft: `${padding}px`,
        paddingRight: `${padding}px`,
        boxSizing: 'border-box',
        flexShrink: 0,
      }}
    >
      <div
        style={{
          ...style,
          flexBasis: width ? `${width}px` : '100%',
          flexGrow: width ? 0 : 1,
          flexShrink: shrink || !width ? 1 : 0,
          minWidth: '0px',
          height: fitHeight ? '100%' : 'auto',
          position: 'relative',
          boxSizing: 'border-box',
        }}
      >
        {children}
      </div>
    </div>
  );
}

export default ContentArea;
