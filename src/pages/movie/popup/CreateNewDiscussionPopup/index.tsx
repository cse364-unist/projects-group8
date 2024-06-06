import React, { useState } from 'react';
import './style.css';

interface CreateNewDiscussionPopupProps {
  onClose: () => void;
  onCreate: (title: string, points: string, startTime: string) => void;
}

const CreateNewDiscussionPopup: React.FC<CreateNewDiscussionPopupProps> = ({ onClose, onCreate }) => {
  const [title, setTitle] = useState('');
  const [points, setPoints] = useState('');
  const [startTime, setStartTime] = useState('');

  const handleSubmit = () => {
    onCreate(title, points, startTime);
    onClose();
  };

  return (
    <div className="popup">
      <h2>Create New Discussion</h2>
      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <input
        type="text"
        placeholder="Discussion points"
        value={points}
        onChange={(e) => setPoints(e.target.value)}
      />
      <input
        type="datetime-local"
        value={startTime}
        onChange={(e) => setStartTime(e.target.value)}
      />
      <button onClick={handleSubmit}>Create</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CreateNewDiscussionPopup;